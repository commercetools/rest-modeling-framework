<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Request;

use Test\Client\ApiRequest;
use Test\Base\JsonObject;

use Test\Base\ResultMapper;
use Psr\Http\Message\ResponseInterface;

class ByProjectCategoriesByIdGet extends ApiRequest
{
    const RESULT_TYPE = JsonObject::class;

    /**
     * @param $project
     * @param $id
     * @param $body
     * @param array $headers
     */
    public function __construct($project, $id, $body = null, array $headers = [])
    {
        $uri = sprintf('/%25s/categories/%25s', $project, $id);
        parent::__construct('get', $uri, $headers, !is_null($body) ? json_encode($body) : null);
    }

    /**
     * @param ResponseInterface $response
     * @return JsonObject
     */
    public function map(ResponseInterface $response, ResultMapper $mapper)
    {
        return parent::map($response, $mapper);
    }

}
