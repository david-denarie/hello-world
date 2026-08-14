import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextReverserService } from './text-reverser.service';

/**
 * Composant principal : champ de saisie + bouton « Envoyer ».
 * Au clic, il appelle l'API REST via TextReverserService et affiche
 * le texte inversé (ou un message d'erreur).
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  text = '';
  reversed = '';
  error = '';
  loading = false;
  hasResult = false;

  constructor(private reverser: TextReverserService) {}

  send(): void {
    this.loading = true;
    this.error = '';

    this.reverser.reverse(this.text).subscribe({
      next: (response) => {
        this.reversed = response.reversed;
        this.hasResult = true;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors de l\'appel à l\'API : ' + (err.status || err.message);
        this.hasResult = true;
        this.loading = false;
      }
    });
  }
}
