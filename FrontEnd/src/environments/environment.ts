export const environment = {
  production: false,
  serverUrl: 'http://localhost:9999',
  keycloak: {
    // Url of the Identity Provider
    issuer: 'http://localhost:8080',
    // Realm
    realm: 'Test',
    clientId: 'Frontend',
  },
};