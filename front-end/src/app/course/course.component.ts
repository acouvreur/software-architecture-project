import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AllService } from '../services/all.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  registerForm: FormGroup;
  isLoading = false;
  submitted = false;
  message = null;

  constructor(
    private formBuilder: FormBuilder,
    private allService: AllService
  ) { }


  ngOnInit() {
    this.registerForm = this.formBuilder.group({
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

    
    this.allService.notify(this.f.type.value)
      .then(res => {
          if (res.id) { 
            this.message = { type: 'success', text: "Status set" };
          } else {
            this.message = { type: 'error', text: res.error };
            this.isLoading = false;
          } 
      });
  }
}

