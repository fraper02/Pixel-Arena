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
![Immaggine](Users/manursp02/Desktop/MainMenu.png)
### Scelta personaggi
### Gioco in esecuzione
![ScreenShotMappa](https://github.com/fraper02/Pixel-Arena/assets/114185914/0ddd0e95-1844-4625-b91a-1fdd84aa182d)
Un esempio di mappa con visualizzazione delle aree di ricerca dei nemici

## Pathfinding & Attacco

Il pathfinding dei nemici avviene tramite l'algoritmo di A* indicizzato, la cui implementazione è fornita nella libreria di libGDX "GDX.ai",
grazie a questo il nemico riuscirà a raggiungere il giocatore quando quest'ultimo entrerà nella propria area di azione.
Per l'attacco l'agente si comporterà come un agente reattivo semplice, di fatti è fornito di un'actionArea che funge da sensore, quando il giocatore entrerà nell'actionArea del nemico, questo inizierà ad attaccarlo.

![ScreenShotAttacci](https://github.com/fraper02/Pixel-Arena/assets/114185914/3693a25a-f216-4747-bfab-ff3156354242)

## Comandi di gioco

Il giocatore potrà muovere il proprio personaggio con la pressione prolungata  dei tasti WASD, questi lo sposteranno nelle 4 direzioni: W per il nord, A per l'ovest, D per l'est e S per il sud.
Il giocatore potrà anche attivare o disattivare la corsa premendo il tasto LSHIFT, mentre per attaccare il nemico bisognerà premere il tasto K.

## Avvio da Android Studio

Per avviare il progetto da Android Studio per prima cosa sarà necessario clonare la repository
```bash
  git clone https://github.com/fraper02/Pixel-Arena.git
```
dopodicché bisognerà creare una nuova configurazione per poter fare il run del progetto, le istruzioni a questo link: https://libgdx.com/wiki/start/import-and-running
