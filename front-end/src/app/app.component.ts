import { Component } from '@angular/core';
import { AllService } from './services/all.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = "Mobile Tracking App"
  username = null;

  constructor(private allService: AllService) 
  {
    this.allService.headerTitle.subscribe(value => {
      this.title = value;
    });

    this.allService.username.subscribe(value => {
      this.username = value;
    });
  }


}
