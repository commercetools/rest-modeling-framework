<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Animal extends JsonObject {
    const DISCRIMINATOR = 'kind';
    const SUB_TYPES = [
        'cat' => Types\Cat::class,

    ];

    const FIELD_KIND = 'kind';

    /**
     * @return string
     */
    public function getKind();
    /**
     * @param string $kind
     * @return $this
     */
    public function setKind($kind);

}
