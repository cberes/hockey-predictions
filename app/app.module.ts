import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule }    from '@angular/http';

import { GameService } from './game.service';
import { APP_CONFIG, HOCKEY_DI_CONFIG } from './app.config';
import { routing } from './app.routes';
import { EncodePipe } from './encode.pipe';
import { AppComponent } from './app.component';
import { RecentGamesComponent } from './recent-games.component';
import { UpcomingGamesComponent } from './upcoming-games.component';
import { SingleGameComponent } from './single-game.component';
import { GameDetailComponent } from './game-detail.component';
import { FacebookShareComponent } from './facebook-share.component';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    routing,
  ],
  declarations: [
    AppComponent,
    RecentGamesComponent,
    UpcomingGamesComponent,
    SingleGameComponent,
    GameDetailComponent,
    FacebookShareComponent,
    EncodePipe,
  ],
  providers: [
    GameService,
    { provide: APP_CONFIG, useValue: HOCKEY_DI_CONFIG },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

