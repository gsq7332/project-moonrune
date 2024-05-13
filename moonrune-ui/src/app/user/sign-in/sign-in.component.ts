import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent {

  username: string = ""
  password: string = ""
  user ?: User

  constructor(private userService: UserService) {}

  signIn() {
    this.userService.signIn(this.username, this.password)
  }
}
