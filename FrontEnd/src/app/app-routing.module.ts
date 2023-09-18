import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateContactComponent } from './admin/create-contact/create-contact.component';
import { TableComponent } from './admin/table/table.component';
import { UpdateContactComponent } from './admin/update-contact/update-contact.component';

const routes: Routes = [
  { path: 'create-contact', component: CreateContactComponent },
  { path: 'admin-table', component: TableComponent },
  { path: 'update-contact/:id', component: UpdateContactComponent },
  //{ path: 'employee-details/:id'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
