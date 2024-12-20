Voici une version corrigée et légèrement améliorée de votre README :

TinyLDF - Linked Data Fragment Server
Contexte
Ce projet a pour objectif de développer un serveur simplifié de fragments de données liées (Tiny Linked Data Fragment Server) basé sur l'exemple de Wikidata LDF. L'application doit permettre :
De traiter des requêtes avec des motifs quadruples.
De gérer les données efficacement tout en autorisant les utilisateurs authentifiés à ajouter de nouveaux quadruples.
Exemples d'applications similaires
Serveur LDF de Wikidata : https://query.wikidata.org/
Client SPARQL Comunica : https://query.comunica.dev/

Objectifs
Backend (Java sur Google App Engine)
Fournir une API REST permettant :
L'insertion de nouveaux quadruples (authentification requise).
Les requêtes sur des motifs quadruples via une API REST.
La gestion de la pagination pour un accès efficace aux données.
Implémentation sur Google App Engine.
Frontend (JavaScript)
Permettre l'insertion de quadruples et la gestion des requêtes.
Afficher les résultats des requêtes sous forme de tableau.
Gérer l'authentification des utilisateurs.
Afficher le temps d'exécution pour chaque requête.

Ce qui a été réalisé
Backend
API REST implémentée avec les points d'entrée suivants :
GET /getQuads : Permet de requêter des quadruples en fonction des paramètres fournis.
POST /insertQuad : Permet d'ajouter un quadruple après authentification.
Gestion des filtres pour les requêtes de quadruples.
Configuration et structure du projet :
pom.xml : Configuration des dépendances Maven.
appengine-web.xml : Configuration pour Google App Engine.
Points de terminaison créés dans la classe QuadEndpoint.
Frontend
Formulaires d'insertion et de requête des quadruples implémentés.
Gestion de la pagination et affichage des résultats sous forme de tableau.
Intégration avec les points d'entrée de l'API REST.

Déploiement
L'application a été déployée sur Google App Engine.


URLs de l'application :


https://fiery-catwalk-445221-j2.ew.r.appspot.com/

https://new-tinyldf-datastore.ew.r.appspot.com/

Dépôt GitHub : https://github.com/khadimfall2/LDF.git



Utilisation
Cloner le dépôt
git clone https://github.com/khadimfall2/LDF.git

Accéder au frontend localement
Placez-vous dans le répertoire webapp :
 cd LDF/src/main/webapp


Ouvrez le fichier wiki.html dans un navigateur :
 firefox wiki.html
 Vous accéderez localement à l'interface de l’application.

Ce qu'il reste à faire
Correction des erreurs API
Résoudre le problème de 404 Page Not Found lors des requêtes API (GET et POST).
Vérifier les configurations dans appengine-web.xml et l'implémentation des endpoints backend.
Intégration backend-frontend
S'assurer que le frontend interagit correctement avec le backend pour les requêtes et les insertions.
Tester les scénarios de bout en bout pour des requêtes réelles de quadruples.
Chargement de données réelles
Charger un jeu de données de quadruples dans Google Datastore pour effectuer des tests réels.
Mesures de performance
Mesurer le temps moyen d'exécution pour les requêtes de quadruples.
Fournir un rapport avec l'écart-type pour évaluer la performance du système.

Auteurs
Khadim FALL
Nafyssata


