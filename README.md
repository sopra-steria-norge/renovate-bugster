# Renovate-bugster
Applikasjon for workshopen "Java Craftmanship - Renoveringen"

## Utgangspunkt
En inhouse utvikler har gått banans i repoet og refaktorert hele applikasjonen,
han er strålende fornøyd med resultatet men sliter med å få den til å bygge igjen..
Han er blant annet ganske grønn på maven..

Kan du hjelpe han få applikasjonen opp å kjøre slik at kjernefunksjonaliteten fungerer?

### Kjerne funksjonalitet
Applikasjonen skal som et minimum dekke følgende funksjonalitet
- Polling av vglive.no
    - Henter ut og lagrer lag
    - Henter ut og lagrer kamper
    - Henter ut og lagrer resultater
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
        
        
#### Utviklerverktøy
[JSON-formatter til Chrome](https://chrome.google.com/webstore/detail/json-formatter/bcjindcccaagfpapjjmafapmmgkkhgoa?utm_source=chrome-app-launcher-info-dialog)

