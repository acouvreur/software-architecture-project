import { Component, OnInit } from '@angular/core';
import { AllService } from '../services/all.service';

@Component({
  selector: 'app-good',
  templateUrl: './good.component.html',
  styleUrls: ['./good.component.css']
})
export class GoodComponent implements OnInit {

  isLoading = false;
  message = null;

  constructor(private allService: AllService) { }

  ngOnInit() {
    this.allService.printInfo();
  }

  estimateBilling() {
    this.isLoading = true;
    this.message = { type: 'processing', text: "Processing..." }; 
    
    this.allService.estimateBilling()
      .then(res => {
          this.message = { type: 'success', text: res };
          this.isLoading = false;
      });
  }

  checkStatus() {
    this.isLoading = true;
    this.message = { type: 'processing', text: "Processing..." };    
    
    this.allService.checkStatus()
      .then(res => {
          this.message = { type: 'success', text: res };
          this.isLoading = false;
      });
  }
}
