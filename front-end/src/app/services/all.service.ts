import { Injectable } from '@angular/core';
import {Subject} from "rxjs"; 
import {environment} from "../../environments/environment";

@Injectable({ providedIn: 'root' })
export class AllService {

    headerTitle = new Subject<string>();
    username = new Subject<string>();
    
    constructor() { }

    printInfo(){
        console.log("I'm user '" + this.getCurrentFirstName() + "' with id: " + this.getCurrentUserId());
        console.log("Good id is: " + this.getCurrentGoodId() + "  and course id is: " + this.getCurrentCourseId());
    }

    setHeaderTitle(title: string) {
        this.headerTitle.next(title);
    }

    getCurrentUserId() {
        return sessionStorage.getItem("userId");
    } 

    getCurrentFirstName() {
        return sessionStorage.getItem("firstName");
    } 

    getCurrentGoodId() {
        return localStorage. getItem("good");
    }

    getCurrentCourseId() {
        return localStorage. getItem("course");
    }


    register(email:string, username:string, firstName:string, lastName:string)
    {
        const url = environment.baseUrl + '8081/accounts';
        
        return fetch(url, { 
            method: "POST", 
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ "email": email, "username":username, "firstName": firstName, "lastName": lastName })
        })
        .then(data => {
            return data.json();
        }).then(res => {
            if(res.id) {
                sessionStorage.setItem("userId", res.id);
                sessionStorage.setItem("firsName", firstName);
                this.username.next(firstName);
            }
            return res;
        });
    }


    announcement(startPoint:string, endPoint:string, startDate:string, endDate:string, type:string) {
        const url = environment.baseUrl + '8080/announcements';
        
        return fetch(url, { 
            method: "POST", 
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                "idTransmitter": this.getCurrentUserId(),
                "nameTransmitter": this.getCurrentFirstName(),
                "startPoint": startPoint, 
                "endPoint":endPoint, 
                "startDate": startDate, 
                "endDate": endDate,
                "type": type,
            })
        })
        .then(data => {
            return data.json();
        }).then(res => {
            if(res.id) {
                if(type == "good") {
                    localStorage.setItem("good", res.id);
                } else if (type == "course") {
                    localStorage.setItem("course", res.id);
                }
            }
            return res;
        });
    }

    notify(status:string) {
        const url = environment.baseUrl + ':8085/tracking/' + this.getCurrentGoodId();
        
        return fetch(url, { 
            method: "PATCH", 
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: status
        })
        .then(data => {
            return data.json();
        }).then(res => {
            console.log(res);
            return res;
        });
    }

    /* TODO: VERIFY IF QUERY IS GOOD LIKE THAT */
    estimateBilling() {
        const url = environment.baseUrl + '8082/billing/estimate';
        
        return fetch(url, { 
            method: "POST", 
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify([
                '{"id": ' + this.getCurrentGoodId +',"startPoint":"Sophia","endPoint":"Paris"}',
                '{"id": ' + this.getCurrentCourseId +',"startPoint":"Sophia","endPoint":"Paris"}'
            ])
        })
        .then(data => {
            return data.json();
        });
    }

    checkStatus() {
        const url = environment.baseUrl + ':8085/tracking/' + this.getCurrentGoodId();
        
        return fetch(url)
        .then(data => {
            return data.json();
        });
    }

}
