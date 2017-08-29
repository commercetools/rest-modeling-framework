<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Client;

use GuzzleHttp\Client;

class CredentialTokenProvider implements TokenProvider
{
    const GRANT_TYPE = 'grant_type';
    const GRANT_TYPE_CLIENT_CREDENTIALS = 'client_credentials';
    const CLIENT_ID = 'clientId';
    const CLIENT_SECRET = 'clientSecret';
    const SCOPE = 'scope';
    const ACCESS_TOKEN = 'access_token';
    const EXPIRES_IN = 'expires_in';

    private $client;
    private $credentials;
    private $accessTokenUrl;

    public function __construct(Client $client, $accessTokenUrl, $credentials)
    {
        $this->accessTokenUrl = $accessTokenUrl;
        $this->client = $client;
        $this->credentials = $credentials;
    }

    public function getToken()
    {
        $data = [
            self::GRANT_TYPE => self::GRANT_TYPE_CLIENT_CREDENTIALS
        ];
        if (isset($this->credentials[self::SCOPE])) {
            $data[self::SCOPE] = $this->credentials[self::SCOPE];
        }
        $options = [
            'form_params' => $data,
            'auth' => [$this->credentials[self::CLIENT_ID], $this->credentials[self::CLIENT_SECRET]]
        ];

        $result = $this->client->post($this->accessTokenUrl, $options);

        $body = json_decode($result->getBody(), true);
        return [$body[self::EXPIRES_IN], $body[self::ACCESS_TOKEN]];
    }
}
