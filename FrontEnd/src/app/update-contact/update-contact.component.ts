import { Component } from '@angular/core';
import {Contact} from "../contact";
import {ContactService} from "../contact.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent {

  id: number = this.route.snapshot.params['id'];
  contact: Contact = new Contact()
  constructor(private contactService: ContactService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.contactService.getContactById(this.id).subscribe(data => {
      this.contact = data;
    }, error => console.log(error));
  }

  onSubmit() {
    this.contactService.updateContact(this.id, this.contact).subscribe(data => {
      this.goToContactList();
    }, error => console.log(error));
  }

  goToContactList(){
    this.router.navigate(['/contacts']);
  }
}
