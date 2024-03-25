import { Component, Input } from '@angular/core';
import { Term } from '../../terms/term';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-term-details',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './term-details.component.html',
  styleUrl: './term-details.component.css'
})
export class TermDetailsComponent {
  @Input() term?: Term;


}
