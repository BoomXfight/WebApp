import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'dashboard-component',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  isAdmin = false;

  constructor(private keycloakService: KeycloakService, private router: Router) {}
  logout() {
    this.keycloakService.logout();
  }

  redirectToEmployee() {
    this.router.navigate(['create-employee']);
  }

  ngOnInit(): void {
    const roles = this.keycloakService.getUserRoles(); // This method depends on your implementation
    this.isAdmin = roles.includes('admin');
  }
}
