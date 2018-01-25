<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Document extends JsonObject {
    const FIELD_CREATED_AT = 'createdAt';

    /**
     * @return \DateTimeImmutable
     */
    public function getCreatedAt();

    /**
     * @param \DateTimeImmutable $createdAt
     * @return $this
     */
    public function setCreatedAt($createdAt);

}
