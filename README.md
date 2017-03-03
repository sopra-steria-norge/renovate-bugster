# Renovate-bugster
Tilpasset fagdag 2017

## Utgangspunkt
NorBets er et nyoppstartet bettingselskap lokalisert på Malta. Forrige utvikler mente at applikasjonen var nesten klar, 
men etter å ha tatt en rask kikk på kildekoden ser du at det trengs å gjøres noen endringer. Det er flere strukturelle utfordringer,
og deler av koden kunne vært mer lesbar. Du mistenker også at det kan være flere bugs i applikasjonen som må lukes ut. 

Testere har rapportert om 
helt fornøyde. 

K
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
        
        
### Utvidelser
Sven skulle gjerne sett at det var mulig å gruppere ting som hører sammen under en paraply. F.eks ved turneringer.

#### Tips & Utviklerverktøy
**NB!** Har du husket å oppdatere settings.xml slik at du får kontakt med maven central?
[JSON-formatter til Chrome](https://chrome.google.com/webstore/detail/json-formatter/bcjindcccaagfpapjjmafapmmgkkhgoa?utm_source=chrome-app-launcher-info-dialog)

