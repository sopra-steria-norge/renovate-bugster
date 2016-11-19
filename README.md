# renovate-bugster
Applikasjon for "Java Craftmanship - Renoveringen"

# Utgangspunkt
En inhouse utvikler har gått banans i repoet og refactorert hele applikasjonen,
han er strålende fornøyd med resultatet men sliter med å få den til å bygge igjen..
Han er blant annet ganske grønn på maven..
Kan du hjelpe han?

# Kjerne funksjonalitet appen skal dekke.
- REST-grensesnitter hvor det kan gjøre følgende
    - Lag
        - Liste ut alle lag
        - Søke på lag
        - Liste ut alle kampene for et lag
    - Re
    - Liste ut dagens sportsbegivenheter
    - Filtrere dagens begivenheter basert på tilstand

    - Kamper
        - Liste ut alle kamper
        - Søke opp kamp på id
        - Søke etter kamper hvor lag har spilt
        - Søke etter kamper på en gitt dag
- Henter ut resultater sports-hendelser fra VG-live for så å lagre disse.