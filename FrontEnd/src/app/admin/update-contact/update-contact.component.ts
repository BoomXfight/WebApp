import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Contact } from 'src/app/contact';
import { ContactService } from 'src/app/contact.service';

@Component({
  selector: 'update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent implements OnInit {

  id: number = 0;
  contact: Contact = new Contact();

  constructor(private service: ContactService, private route: ActivatedRoute, private router: Router,
              private toast: ToastrService) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.service.getContactById(this.id).subscribe( con => {
      this.contact = con;
    }, error => console.log(error));
  }

  onSubmit() {
    this.service.updateContact(this.id, this.contact).subscribe( response => {
      this.toast.success("Contact successfully updated!", "Success");
      this.router.navigate(['admin-table']);
    },
    error => {
      console.log(error);
      this.toast.error(error.error, "Error");
    })
  }
}
