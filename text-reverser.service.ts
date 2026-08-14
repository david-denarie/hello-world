import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/** Réponse renvoyée par l'API REST back-end. */
export interface TextResponse {
  original: string;
  reversed: string;
}

/**
 * Service qui communique avec l'API REST Spring Boot.
 * L'URL /api/reverse est relative : en dev elle est redirigée vers
 * http://localhost:8080 grâce au proxy (proxy.conf.json).
 */
@Injectable({ providedIn: 'root' })
export class TextReverserService {

  private readonly apiUrl = '/api/reverse';

  constructor(private http: HttpClient) {}

  reverse(text: string): Observable<TextResponse> {
    return this.http.post<TextResponse>(this.apiUrl, { text });
  }
}
