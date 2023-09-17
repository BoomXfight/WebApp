import { Component, ViewChild } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from '../contact';
import { MatTableDataSource } from '@angular/material/table';
import { MatTable } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'user-component',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {

  contactList!: Contact[];
  dataSource: any;
  displayedColumns: String[] = ["firstName", "lastName", "email", "phoneNumber", "age", "country", "city", "street", "houseNumber"];
  @ViewChild(MatPaginator) paginatior !: MatPaginator;
  @ViewChild(MatSort) sort !: MatSort;

  constructor(private service: ContactService) {
    this.service.getContactList().subscribe(res => {
      this.contactList = res;
      this.dataSource = new MatTableDataSource<Contact>(this.contactList);
      this.dataSource.paginator = this.paginatior;
      this.dataSource.sort = this.sort;
    })
  }

  Filterchange(data: Event) {
    const value = (data.target as HTMLInputElement).value;
    this.dataSource.filter = value;
  }
}
