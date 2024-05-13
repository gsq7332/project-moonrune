import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../user';

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

  constructor(private userService: UserService) {}

  signUp() {
    this.userService.createUser(this.username, this.password)
  }

}
