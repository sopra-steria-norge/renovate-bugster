# Renovate-bugster
Tilpasset fagdag 2017

## Utgangspunkt
NorBets er et nyoppstartet bettingselskap lokalisert på Malta. Forrige utvikler mente at applikasjonen var nesten klar, 
men etter å ha tatt en rask kikk på kildekoden ser du at det trengs å gjøres noen endringer. Det er flere strukturelle utfordringer,
og deler av koden kunne vært mer lesbar. 

Testere har rapportert om utbetalinger som ikke blir gjennomført og utbetalinger som blir feil. 
Du mistenker også at det kan være flere bugs i applikasjonen som må lukes ut. 

### Oppdrag

 - Du skal fikse feilene i applikasjonen
      - Det er meningen at det skal jobbes testdrevet - tester skal avdekke feil før feilen rettes
      - Merk at deler av koden kan vise seg vanskelig å skrive tester for uten omskriving
 - Refaktorere applikasjonen etter beste evne
 - Utvide applikasjonen med funksjonalitet for å sette inn og ta ut penger

### Diskusjonspunkter

 - Hva burde vært endret i applikasjonen
 - Hvordan kan man gå frem for å gjøre dette
 - Hvordan fungerer utstrakt bruk av Mockito når man refaktorer kode?


### Ekstraoppgaver
 - Tilby mulighet til å tippe også på kamper som skjer frem i tid (pr. nå 'støtte' kun dagens kamper)
 - Grensesnitt for å søke etter kamper på en gitt dag
 - Grensesnitt for å søke etter kamper på en gitt dag
 - Grensesnitt for å søke ut dagens begivenheter basert på tilstand
 - Grensesnitt for å søke opp et gitt lag
        
        
### Utvidelser
Sven skulle gjerne sett at det var mulig å gruppere ting som hører sammen under en paraply. F.eks ved turneringer.

#### Tips & Utviklerverktøy
**NB!** Har du husket å oppdatere settings.xml slik at du får kontakt med maven central?
[JSON-formatter til Chrome](https://chrome.google.com/webstore/detail/json-formatter/bcjindcccaagfpapjjmafapmmgkkhgoa?utm_source=chrome-app-launcher-info-dialog)
