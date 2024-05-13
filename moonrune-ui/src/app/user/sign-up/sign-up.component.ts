import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../user';
import { Router } from '@angular/router';
import { Cookie } from 'ng2-cookies';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {

  username: string = ""
  password: string = ""
  user ?: User
  failedSignup: boolean = false

  constructor(private userService: UserService, private route: Router) {}

  signUp() {
    this.userService.createUser(this.username, this.password).subscribe(user => {
      this.user = user;
      if (user == undefined) {
        this.username = ""
        this.password = ""
        this.failedSignup = true
      } else {
        Cookie.set("username", this.username)
        this.route.navigate(['/mainpage'])
      }
    })
  }

}
