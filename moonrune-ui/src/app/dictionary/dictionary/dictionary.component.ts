import { Component } from '@angular/core';
import { ListEntryComponent } from '../list-view/list-entry/list-entry.component';
import { TermsListComponent } from '../list-view/terms-list/terms-list.component';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-dictionary',
  standalone: true,
  imports: [TermsListComponent, RouterLink],
  templateUrl: './dictionary.component.html',
  styleUrl: './dictionary.component.css'
})
export class DictionaryComponent {
  constructor(private route: ActivatedRoute) {}

  id: number = Number(this.route.snapshot.paramMap.get('id'))
}
