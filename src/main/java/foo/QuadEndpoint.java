package foo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


@Api(
    name = "myApi",
    version = "v1",
    namespace = @ApiNamespace(
        ownerDomain = "helloworld.example.com",
        ownerName = "helloworld.example.com",
        packagePath = ""
    )
)
public class QuadEndpoint {

    @ApiMethod(name = "getQuads", httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Entity> getQuads(
        @Named("subject") @Nullable String subject,
        @Named("predicate") @Nullable String predicate,
        @Named("object") @Nullable String object,
        @Named("graph") @Nullable String graph,
        @Nullable @Named("cursor") String cursorString
    ) {
        List<Filter> filters = new ArrayList<>();
        if (subject != null) filters.add(new FilterPredicate("subject", Query.FilterOperator.EQUAL, subject));
        if (predicate != null) filters.add(new FilterPredicate("predicate", Query.FilterOperator.EQUAL, predicate));
        if (object != null) filters.add(new FilterPredicate("object", Query.FilterOperator.EQUAL, object));
        if (graph != null) filters.add(new FilterPredicate("graph", Query.FilterOperator.EQUAL, graph));

        Filter finalFilter = filters.isEmpty() ? null : new CompositeFilter(CompositeFilterOperator.AND, filters);

        Query q = new Query("Quad");
        if (finalFilter != null) q.setFilter(finalFilter);

        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(100);
        if (cursorString != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(cursorString));
        }

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        QueryResultList<Entity> results = datastore.prepare(q).asQueryResultList(fetchOptions);

        String nextCursor = results.getCursor() != null ? results.getCursor().toWebSafeString() : null;

        return CollectionResponse.<Entity>builder()
                .setItems(results)
                .setNextPageToken(nextCursor)
                .build();
    }

    @ApiMethod(name = "insertQuad", httpMethod = ApiMethod.HttpMethod.POST)
    public Entity insertQuad(
        User user,
        @Named("subject") String subject,
        @Named("predicate") String predicate,
        @Named("object") String object,
        @Named("graph") String graph
    ) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authentication required to insert a quad.");
        }

        if (subject == null || predicate == null || object == null || graph == null) {
            throw new IllegalArgumentException("All fields (subject, predicate, object, graph) are required.");
        }

        Entity quad = new Entity("Quad");
        quad.setProperty("subject", subject);
        quad.setProperty("predicate", predicate);
        quad.setProperty("object", object);
        quad.setProperty("graph", graph);
        quad.setProperty("owner", user.getEmail());
        quad.setProperty("date", new Date());

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(quad);

        return quad;
    }
}
