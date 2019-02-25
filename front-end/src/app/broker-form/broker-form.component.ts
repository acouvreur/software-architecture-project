import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-broker-form',
  templateUrl: './broker-form.component.html',
  styleUrls: ['./broker-form.component.css']
})
export class BrokerFormComponent implements OnInit 
{
  @Input() numSection: string;

  brokerForm: FormGroup;
  isLoading = false;
  submitted = false;
  message = null;
  sectionList = ["Accounting", "Announcement", "Matching", "Tracking", "Billing"];
  urlList = ["http://localhost:8081/broker", "http://localhost:8080/broker", "http://localhost:8084/broker", "http://localhost:8085/broker", "http://localhost:8082/broker"];


  constructor(private formBuilder: FormBuilder) { }


  ngOnInit() {
    this.brokerForm = this.formBuilder.group({
        slowdown: ['', Validators.required],
        delete: ['', Validators.required],
        duplicate: ['', Validators.required],
        salt: ['', Validators.required],
        nothing: ['', Validators.required]
    });

    if(this.numSection == "2") {
        console.log("I'm " + this.sectionList[this.numSection] + " ALLER CHERCHER GET");
        fetch(this.urlList[this.numSection])
            .then(data => {
                return data.json();
            })
            .then(res => {
                console.log("Response");
                console.log(res);
                this.f.delete.setValue(res.pDelete);
                this.f.duplicate.setValue(res.pDuplicate);
                this.f.salt.setValue(res.pSalt);
                this.f.slowdown.setValue(res.pSlow);
                this.f.nothing.setValue(res.pNothing);
            });
    }
  }


  // convenience getter for easy access to form fields
  get f() { return this.brokerForm.controls; }


  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.brokerForm.invalid) {
        return;
    }
    this.isLoading = true;  
    
    let values = '{ "pSlow": ' + this.f.slowdown.value + ', "pDelete":' + this.f.delete.value + ', "pDuplicate": ' + this.f.duplicate.value + ', "pSalt": ' + this.f.salt.value + ', "pNothing": ' + this.f.nothing.value + '}';
    console.log(values);
    
    fetch(this.urlList[this.numSection], { 
        method: "PATCH", 
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: values
    })
    .then(data => {
        console.log("respuestaa");
        console.log(data.json());
        this.message = { type: 'success', text: "Modifcation submitted successfuly" };
        this.isLoading = false;
        this.submitted = false;
    });
  }
}
