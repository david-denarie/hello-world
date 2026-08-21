import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { TextReverserService } from '../../text-reverser.service';

@Component({
  selector: 'app-uppercase',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './uppercase.component.html',
  styleUrl: './uppercase.component.css'
})
export class UppercaseComponent {

  text = '';
  result = '';
  error = '';
  loading = false;
  hasResult = false;

  constructor(
    private service: TextReverserService,
    private cdr: ChangeDetectorRef
  ) {}

  send(): void {
    this.loading = true;
    this.error = '';

    this.service.uppercase(this.text).subscribe({
      next: (response) => {
        this.result = response.result;
        this.hasResult = true;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.error = 'Erreur lors de l\'appel à l\'API : ' + (err.status || err.message);
        this.hasResult = true;
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
}
