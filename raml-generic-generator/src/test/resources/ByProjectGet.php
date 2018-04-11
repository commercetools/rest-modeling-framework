<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Request;

use Test\Client\ApiRequest;
use Test\Base\JsonObject;

use Test\Base\ResultMapper;
use Psr\Http\Message\ResponseInterface;

class ByProjectGet extends ApiRequest
{
    const RESULT_TYPE = JsonObject::class;

    /**
     * @param $project
     * @param $body
     * @param array $headers
     */
    public function __construct($project, $body = null, array $headers = [])
    {
        $uri = str_replace(['{project}'], [$project], '/{project}');
        parent::__construct('get', $uri, $headers, !is_null($body) ? json_encode($body) : null);
    }

    /**
     * @param ResponseInterface $response
     * @param ResultMapper $mapper
     * @return JsonObject
     */
    public function map(ResponseInterface $response, ResultMapper $mapper):  JsonObject
    {
        return parent::map($response, $mapper);
    }

}
