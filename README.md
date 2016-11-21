# renovate-bugster
Applikasjon for workshopen "Java Craftmanship - Renoveringen"

# Utgangspunkt
En inhouse utvikler har gått banans i repoet og refaktorert hele applikasjonen,
han er strålende fornøyd med resultatet men sliter med å få den til å bygge igjen..
Han er blant annet ganske grønn på maven..
Kan du hjelpe han?

# Kjerne funksjonalitet appen skal dekke.

- REST-grensesnitt
    - Lag
        - Liste ut alle lag
        - Søke på lag
        - Liste ut alle kampene for et lag

    - Live
        - Liste ut dagens sportsbegivenheter
        - Filtrere dagens begivenheter basert på tilstand

    - Kamper
        - Liste ut alle kamper
        - Søke opp kamp på id
        - Søke etter kamper hvor lag har spilt
        - Søke etter kamper på en gitt dag

- Polling
    - Henter ut og lagrer kamper, lag og resultat kontinuerlig
