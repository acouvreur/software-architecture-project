import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrokerFormComponent } from './broker-form/broker-form.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { BrokerDashboardComponent } from './broker-dashboard/broker-dashboard.component';
import { AnnouncementComponent } from './announcement/announcement.component';
import { CourseComponent } from './course/course.component';
import { GoodComponent } from './good/good.component';

@NgModule({
  declarations: [
    AppComponent,
    BrokerFormComponent,
    HomeComponent,
    RegisterComponent,
    BrokerDashboardComponent,
    AnnouncementComponent,
    CourseComponent,
    GoodComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
