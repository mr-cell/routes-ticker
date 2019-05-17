# routes-ticker

Idea: 8a.nu clone

Multiple services:
- users-service
- climbing-routes-service (countries, crags, faces, routes)
- reviews-service (routes ticks, reviews, stars)
- auth-service (OAuth2 server)

Technologies:
1. docker
2. service discovery
3. config server
4. docker compose?
5. kafka (event/command based communication)
6. api gateway
7. circuit braker
8. mongodb database - data store for users, routes, reviews, etc
9. ELK stack: log aggregation + search
10. prometheus + graphana: monotoring collection and visualization
11. neo4j database: recommendations of routes to be done based on friends' ascents, top routes in some region, similar routes in the country, etc
12. tracing 
