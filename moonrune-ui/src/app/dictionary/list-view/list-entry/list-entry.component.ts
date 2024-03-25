import { Component } from '@angular/core';
import { TermDetailsComponent } from '../../../general/term-details/term-details.component';

@Component({
  selector: 'app-list-entry',
  standalone: true,
  imports: [TermDetailsComponent],
  templateUrl: './list-entry.component.html',
  styleUrl: './list-entry.component.css'
})
export class ListEntryComponent {
  
}
