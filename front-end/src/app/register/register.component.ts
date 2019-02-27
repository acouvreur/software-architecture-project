import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AllService } from '../services/all.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

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
    this.allService.headerTitle.next("Test");

    this.registerForm = this.formBuilder.group({
        email: ['', Validators.required],
        username: ['', Validators.required],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required]
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

    this.allService.register(this.f.email.value, this.f.username.value, this.f.firstName.value, this.f.lastName.value)
      .then(res => {
          if (res.id) { 
            this.router.navigate(['/announcement']);
          } else {
            this.message = { type: 'error', text: res.error };
            this.isLoading = false;
          } 
      });
  }
}
