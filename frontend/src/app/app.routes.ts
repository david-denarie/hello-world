import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent) },
  { path: 'reverse', loadComponent: () => import('./pages/reverse/reverse.component').then(m => m.ReverseComponent) },
  { path: 'uppercase', loadComponent: () => import('./pages/uppercase/uppercase.component').then(m => m.UppercaseComponent) },
  { path: '**', redirectTo: '' }
];
