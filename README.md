## Authors
- [@fraper02](https://www.github.com/fraper02)
- [@Manursp0022](https://www.github.com/Manursp0022)
- [@DEEECAAAuni](https://www.github.com/DEEECAAAuni)

# Pixel Arena

Pixel Arena è un videogioco platform ortogonale che vedrà il giocatore addentrarsi in una mappa 
piena di nemici dotati di un'AI che cercheranno di fermarlo ad ogni costo.

## Descrizione

Siamo 3 studenti dell'università di salerno che hanno deciso di sviluppare un videogioco come progetto combinato per gli esami di Ingegneria del Software e Fondamenti di Intelligenza Artificiale.
L'idea è nata dal voler fare un progetto differente e abbastanza complesso che potesse mettere alla prova le nostre capacità, dandoci la possibilità anche di imparare nuove tecnologie nell'ambito del game development.
Per la realizzazione di questo progetto abbiamo usato il framework libGDX per il motore di gioco e il programma Tiled per realizzare la mappa, 
il codice è stato scritto in Java usando l'IDE Android Studio ed utilizza come sistema di build gradle.

## Features

L'applicazione prevede all'avvio un menù principale che offre la possibiltà al giocatore di iniziare una nuova partita oppure di caricare un vecchio salvataggio.
Inoltre il giocatore dovrà scegliere il personaggio da utilizzare attraverso un Menù apposito prima di iniziare la partita.
All'avvio della partita il giocatore si ritroverà nella mappa dove dovrà eliminare tutti i nemici per passare al livello successivo,
i nemici saranno dotati di intelligenza e cercheranno il nemico nelle loro aree di competenza, provando a loro volta ad eliminare il giocatore.
In ogni livello saranno seminate basi di cura e delle gemme che il giocatore può raccogliere per generare successivamente dei potenziamenti che saranno scelti a fine livello.

### Menu principale
<img width="1920" alt="MainMenu-min" src="https://github.com/fraper02/Pixel-Arena/assets/114728100/afe9208a-546a-4316-bf6f-d4ddc6704f5d">

### Scelta personaggi
<img width="1918" alt="ChooseCharacter-min" src="https://github.com/fraper02/Pixel-Arena/assets/114728100/1284ac64-1c83-4d9c-bc22-51d827f2591b">

### Gioco in esecuzione
![ScreenShotMappa](https://github.com/fraper02/Pixel-Arena/assets/114185914/0ddd0e95-1844-4625-b91a-1fdd84aa182d)
Un esempio di mappa con visualizzazione delle aree di ricerca dei nemici

## Pathfinding & Attacco

Il pathfinding dei nemici avviene tramite l'algoritmo di A* indicizzato, la cui implementazione è fornita nella libreria di libGDX "GDX.ai",
grazie a questo il nemico riuscirà a raggiungere il giocatore quando quest'ultimo entrerà nella propria area di azione.
Per l'attacco l'agente si comporterà come un agente reattivo semplice, di fatti è fornito di un'actionArea che funge da sensore, quando il giocatore entrerà nell'actionArea del nemico, questo inizierà ad attaccarlo.

![ScreenShotAttacci](https://github.com/fraper02/Pixel-Arena/assets/114185914/3693a25a-f216-4747-bfab-ff3156354242)

## Tutorial[PixelArena]

### Movimento
WASD per Muoversi: Usa i tasti W, A, S, D per muoverti.
- Premi W per muoverti verso Nord.
- Premi A per andare verso Ovest.
- Usa S per dirigersi a Sud.
- Clicca D per spostarti verso Est.
#### Corsa
- Il giocatore potrà anche attivare o disattivare la corsa premendo il tasto LSHIFT, mentre per attaccare il nemico bisognerà premere il tasto K.

### Recupero Salute
- Basi di Guarigione: Trova le basi sparse per il livello.
- Staziona sulla Base: Posizionati su una base per recuperare salute.
- Guarigione: Mentre sei su una base, recupererai 10HP al secondo.

### Raccogliere Gemme 
- Gemme nel Livello: Nel livello sono disseminate gemme.
- Raccogli le Gemme: Cammina sopra le gemme per raccoglierle.

### Nemici e Combattimento
- Vita dei Nemici: I nemici hanno 80HP di vita totale.
- Attacco: Sia tu che i nemici avete un attacco pari a 10.
- Strategia: Usa la tua abilità e astuzia per sconfiggere i nemici.

# Avvio da Android Studio

Per avviare il progetto da Android Studio per prima cosa sarà necessario clonare la repository
```bash
  git clone https://github.com/fraper02/Pixel-Arena.git
```
dopodicché bisognerà creare una nuova configurazione per poter fare il run del progetto, le istruzioni a questo link: https://libgdx.com/wiki/start/import-and-running
