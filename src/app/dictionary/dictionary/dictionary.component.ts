import { Component } from '@angular/core';
import { ListEntryComponent } from '../list-view/list-entry/list-entry.component';
import { TermsListComponent } from '../list-view/terms-list/terms-list.component';

@Component({
  selector: 'app-dictionary',
  standalone: true,
  imports: [TermsListComponent],
  templateUrl: './dictionary.component.html',
  styleUrl: './dictionary.component.css'
})
export class DictionaryComponent {

}
