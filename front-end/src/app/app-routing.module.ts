import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { BrokerDashboardComponent } from './broker-dashboard/broker-dashboard.component';
import { RegisterComponent } from './register/register.component';
import { AnnouncementComponent } from './announcement/announcement.component';
import { CourseComponent } from './course/course.component';
import { GoodComponent } from './good/good.component';


const routes: Routes = [
  { path: 'good', component: GoodComponent},
  { path: 'course', component: CourseComponent},
  { path: 'announcement', component: AnnouncementComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'broker', component: BrokerDashboardComponent},
  { path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
