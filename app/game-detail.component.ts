import { Component, Inject, Input, OnInit } from '@angular/core';

import { APP_CONFIG, AppConfig } from './app.config';
import { Game } from './game';

@Component({
  selector: 'game-detail',
  templateUrl: 'app/game-detail.component.html',
  styleUrls: ['app/game-detail.component.css'],
})
export class GameDetailComponent implements OnInit {
  @Input() game: Game;
  includeScore: boolean;
  displayHomePred: number;
  displayAwayPred: number;

  constructor(@Inject(APP_CONFIG) private config: AppConfig) {}

  ngOnInit() {
    this.includeScore = this.game.status == 'F';

    // if rounding will suggest a tie, then add +/- 1 to one team's predicted score
    let tiePredicted = Math.round(this.game.prediction.away_score) == Math.round(this.game.prediction.home_score);
    let roundedTotal = Math.round(this.game.prediction.away_score) + Math.round(this.game.prediction.home_score);
    let exactTotal = this.game.prediction.away_score + this.game.prediction.home_score;
    let tieOffset = exactTotal > roundedTotal ? 1 : -1;
    let predictedAwayWins = this.game.prediction.away_score > this.game.prediction.home_score;
    let awayOffset = tiePredicted && ((predictedAwayWins && tieOffset > 0) || (!predictedAwayWins && tieOffset < 0)) ? tieOffset : 0;
    let homeOffset = tiePredicted && ((predictedAwayWins && tieOffset < 0) || (!predictedAwayWins && tieOffset > 0)) ? tieOffset : 0;
    this.displayAwayPred = this.game.prediction.away_score + awayOffset;
    this.displayHomePred = this.game.prediction.home_score + homeOffset;
  }
}
