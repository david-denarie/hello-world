import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/** Réponse renvoyée par les API REST de transformation de texte. */
export interface TextResponse {
  original: string;
  result: string;
}

/**
 * Service qui communique avec l'API REST Spring Boot.
 * Les URLs /api/* sont relatives : en dev elles sont redirigées vers
 * https://localhost:8443 grâce au proxy (proxy.conf.json).
 */
@Injectable({ providedIn: 'root' })
export class TextReverserService {

  constructor(private http: HttpClient) {}

  reverse(text: string): Observable<TextResponse> {
    return this.http.post<TextResponse>('/api/reverse', { text });
  }

  uppercase(text: string): Observable<TextResponse> {
    return this.http.post<TextResponse>('/api/uppercase', { text });
  }
}
