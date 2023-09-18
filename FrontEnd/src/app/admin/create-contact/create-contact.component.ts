import { Component, OnInit, resolveForwardRef } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from 'src/app/contact';
import { ContactService } from 'src/app/contact.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'create-contact',
  templateUrl: './create-contact.component.html',
  styleUrls: ['./create-contact.component.css']
})
export class CreateContactComponent {
  contact: Contact = new Contact();
  constructor(private service: ContactService, private router: Router, private toastr: ToastrService) {}

  saveContact() {
    this.contact.address.id = NaN;
    this.service.createContact(this.contact).subscribe( response => {
      this.toastr.success("Contact succesfuly added!", "Success");
      this.router.navigate(['admin-table']);
    },
    error => {
      console.log(error);
      this.toastr.error(error.error, "Error");
    }
    );
  }
  

  onSubmit() {
    console.log(this.contact);
    this.saveContact();
  }
}
