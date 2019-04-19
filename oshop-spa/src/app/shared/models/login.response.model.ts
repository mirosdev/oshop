export interface LoginResponseModel {
  success: boolean;
  authorities: Authorities[];
  token: string;
}

interface Authorities {
  authority: string;
}
