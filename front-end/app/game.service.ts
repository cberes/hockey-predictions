import { Inject, Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { APP_CONFIG, AppConfig } from './app.config';
import { Game } from './game';

@Injectable()
export class GameService {
  constructor(private http: Http,
              @Inject(APP_CONFIG) private config: AppConfig) {}

  getRecentGames(): Promise<Game[]> {
    return this.getGames('games/recent');
  }

  getUpcomingGames(): Promise<Game[]> {
    return this.getGames('games/upcoming');
  }

  getGame(id: number): Promise<Game[]> {
    return this.getGames('games/' + id);
  }

  private getGames(endpoint: string): Promise<Game[]> {
    let url = `//${this.config.apiEndpoint}/${endpoint}`;
    return this.http.get(url)
               .toPromise()
               .then(response => response.json().games)
               .catch(this.handleError);
  }

  private handleError(error: any) {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
