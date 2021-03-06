import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from "../../environments/environment";

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
  sectionList = ["Accounting", "Announcement", "Matching", "Tracking"];
  portList = ["8081", "8080", "8084", "8085"];
  specificUrl = "/broker";


  constructor(private formBuilder: FormBuilder) { }


  ngOnInit() {
    this.brokerForm = this.formBuilder.group({
        slowdown: ['', Validators.required],
        delete: ['', Validators.required],
        duplicate: ['', Validators.required],
        salt: ['', Validators.required],
        nothing: ['', Validators.required]
    });

    let url = environment.baseUrl + this.portList[this.numSection] + this.specificUrl;
    console.log("I'm " + this.sectionList[this.numSection]);

    fetch(url)
        .then(data => {
            return data.json();
        })
        .then(res => {
            this.f.delete.setValue(res.pDelete);
            this.f.duplicate.setValue(res.pDuplicate);
            this.f.salt.setValue(res.pSalt);
            this.f.slowdown.setValue(res.pSlow);
            this.f.nothing.setValue(res.pNothing);
        });
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
    this.message = { type: 'processing', text: "Processing..." };    
    
    let values = '{ "pSlow": ' + this.f.slowdown.value + ', "pDelete":' + this.f.delete.value + ', "pDuplicate": ' + this.f.duplicate.value + ', "pSalt": ' + this.f.salt.value + ', "pNothing": ' + this.f.nothing.value + '}';    
    let url = environment.baseUrl + this.portList[this.numSection] + this.specificUrl;

    fetch(url, { 
        method: "PATCH", 
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: values
    })
    .then(data => {
        this.message = { type: 'success', text: "Done!" };
        this.isLoading = false;
        this.submitted = false;
    });
  }
}
