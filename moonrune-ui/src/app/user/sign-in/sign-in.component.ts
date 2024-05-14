import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../user';
import { Cookie } from 'ng2-cookies';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent {

  username: string = ""
  password: string = ""
  user ?: User
  failedLogin: boolean = false

  constructor(private userService: UserService, private route: Router) {}

  signIn() {
    this.userService.signIn(this.username, this.password).subscribe(user => {
      this.user = user;
      if (user == undefined) {
        this.username = ""
        this.password = ""
        this.failedLogin = true
      } else {
        Cookie.set("username", this.username)
        this.route.navigate(['/mainpage'])
      }
    })  
  }
}
