import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UpcomingGamesComponent } from './upcoming-games.component';
import { RecentGamesComponent } from './recent-games.component';
import { SingleGameComponent } from './single-game.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/upcoming',
    pathMatch: 'full'
  },
  {
    path: 'upcoming',
    component: UpcomingGamesComponent
  },
  {
    path: 'recent',
    component: RecentGamesComponent
  },
  {
    path: 'game/:id',
    component: SingleGameComponent
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
