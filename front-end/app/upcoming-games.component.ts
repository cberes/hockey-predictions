import { Component, OnInit } from '@angular/core';

import { Game } from './game';
import { GameService } from './game.service';

@Component({
  selector: 'upcoming-games',
  templateUrl: 'app/games-list.component.html',
  styleUrls: ['app/games-list.component.css'],
})
export class UpcomingGamesComponent implements OnInit {
  title = 'Upcoming Games';
  games: Game[];

  constructor(private gameService: GameService) { }

  ngOnInit() {
    this.gameService.getUpcomingGames().then(games => this.games = games);
  }
}

