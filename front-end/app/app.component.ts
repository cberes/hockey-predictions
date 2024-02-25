import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  template: `
    <h1>{{title}}</h1>
    <nav>
      <a [routerLink]="['upcoming']" [routerLinkActive]="['router-link-active']">Upcoming</a>
      <a [routerLink]="['recent']" [routerLinkActive]="['router-link-active']">Recent</a>
    </nav>
    <router-outlet></router-outlet>
    <div id="footer">
      <p>
        Follow us for news and updates:
        <a href="https://twitter.com/hockeyscor_es">Twitter</a>
        <a href="https://instagram.com/hockeyscor.es">Instagram</a>
      </p>
      <p>
        &copy; 2016 Corey Beres.
        This site is not associated with any sports teams or leagues.
        These predictions are provided for entertainment only.
        We make no guarantees about the outcomes of these games.
        <a href="http://www.responsiblegambling.org/safer-play">Gamble responsibly.</a>
      </p>
    </div>
  `,
  styleUrls: ['app/app.component.css'],
})
export class AppComponent {
  title = 'Hockey Predictions';
}
