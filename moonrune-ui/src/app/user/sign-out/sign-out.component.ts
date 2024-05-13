import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';

@Component({
  selector: 'app-sign-out',
  standalone: true,
  imports: [],
  templateUrl: './sign-out.component.html',
  styleUrl: './sign-out.component.css'
})
export class SignOutComponent {

  constructor(private route: Router) {}

  signOut() {
    Cookie.delete("username");
    this.route.navigate(['/mainpage'])
  }
}
