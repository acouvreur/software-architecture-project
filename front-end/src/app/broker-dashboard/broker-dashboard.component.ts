import { Component, OnInit } from '@angular/core';
import { AllService } from '../services/all.service';

@Component({
  selector: 'app-broker-dashboard',
  templateUrl: './broker-dashboard.component.html',
  styleUrls: ['./broker-dashboard.component.css']
})
export class BrokerDashboardComponent implements OnInit {

  constructor(private allService: AllService) { }

  ngOnInit() {
    this.allService.headerTitle.next("Chaos Broker Management");
  }

}
