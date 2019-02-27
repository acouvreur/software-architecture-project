import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AllService } from '../services/all.service';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {
  
  registerForm: FormGroup;
  isLoading = false;
  submitted = false;
  message = null;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private allService: AllService
  ) { }


  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      startPoint: ['', Validators.required],
      endPoint: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      type: ['', Validators.required]
    });

    this.allService.printInfo();
  }


  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }


  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }
    this.isLoading = true;
    this.message = { type: 'processing', text: "Processing..." }; 

    
    this.allService.announcement(this.f.startPoint.value, this.f.endPoint.value, this.f.startDate.value, this.f.endDate.value, this.f.type.value)
      .then(res => {
          if (res.id) { 
            if(this.f.type.value == "good") {
                this.router.navigate(['/good']);
            } else if (this.f.type.value == "course") {
              this.router.navigate(['/course']);
            }
          } else {
            this.message = { type: 'error', text: res.error };
            this.isLoading = false;
          } 
      });
  }
}
