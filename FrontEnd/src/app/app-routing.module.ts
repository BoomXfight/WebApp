import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ContactListComponent} from "./contact-list/contact-list.component";
import {CreateContactComponent} from "./create-contact/create-contact.component";
import {UpdateContactComponent} from "./update-contact/update-contact.component";

const routes: Routes = [
  {path: 'contacts', component:  ContactListComponent},
  {path: 'create-contact', component: CreateContactComponent},
  {path: 'update-contact/:id', component: UpdateContactComponent},
  {path: '', redirectTo: 'contacts', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
