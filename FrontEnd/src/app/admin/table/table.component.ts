import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Contact } from 'src/app/contact';
import { ContactService } from 'src/app/contact.service';

@Component({
  selector: 'admin-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {

  contactList!: Contact[];
  dataSource: any;
  displayedColumns: String[] = ["firstName", "lastName", "email", "phoneNumber", "age", "country", "city", "street", "houseNumber", "actions"];
  @ViewChild(MatPaginator) paginatior !: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;

  constructor(private service: ContactService, private router: Router, private toastr: ToastrService) {
    this.fetchData();
  }

/**
 * This method filters the table for a specific substring match
 * @param data 
 */
  Filterchange(data: Event) {
    const value = (data.target as HTMLInputElement).value;
    this.dataSource.filter = value;
  }

  /**
   * This method deletes the contact from the database and informs the user
   * about the successful deletion of the contact
   * @param id 
   */
  deleteContact(id: number) {
    this.service.deleteContact(id).subscribe(response => {
      console.log('Deleted successfully', response);
      this.fetchData();
      this.toastr.success("Contact usccesfully deleted", "Success");
    },
    error => {
      console.log('Error deleting: ', error);
    });
  }

  updateContact(id: number) {
    this.router.navigate(['update-contact', id]);
  }

  /**
   * This method fetches the data from the backend and updates the mat-table content
   */
  fetchData() {
    this.service.getContactList().subscribe(res => {
      this.contactList = res;
      this.dataSource = new MatTableDataSource<Contact>(this.contactList);
      this.dataSource.paginator = this.paginatior;
      this.dataSource.sort = this.sort;
    })
  }


}
